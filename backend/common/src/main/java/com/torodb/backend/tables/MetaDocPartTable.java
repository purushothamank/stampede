package com.torodb.backend.tables;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.impl.TableImpl;

import com.torodb.backend.DatabaseInterface;
import com.torodb.backend.meta.TorodbSchema;
import com.torodb.backend.tables.records.MetaDocPartRecord;

public abstract class MetaDocPartTable<TableRefType, R extends MetaDocPartRecord<TableRefType>> extends SemanticTableImpl<R> {

    private static final long serialVersionUID = 1664366669485866827L;

    public static final String TABLE_NAME = "container";

    public enum TableFields {
        DATABASE               (   "database"          ),
        COLLECTION             (   "collection"        ),
        TABLE_REF              (   "tableRef"          ),
        IDENTIFIER             (   "identifier"        ),
        LAST_RID               (   "last_rid"          )
        ;

        public final String fieldName;

        TableFields(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return fieldName;
        }
    }

    /**
     * The class holding records for this type
     * @return 
     */
    @Override
    public abstract Class<R> getRecordType();

    /**
     * The column <code>torodb.container.database</code>.
     */
    public final TableField<R, String> DATABASE 
            = createDatabaseField();

    /**
     * The column <code>torodb.container.collection</code>.
     */
    public final TableField<R, String> COLLECTION 
            = createCollectionField();

    /**
     * The column <code>torodb.container.path</code>.
     */
    public final TableField<R, TableRefType> TABLE_REF 
            = createTableRefField();

    /**
     * The column <code>torodb.container.table_name</code>.
     */
    public final TableField<R, String> IDENTIFIER 
            = createIdentifierField();

    /**
     * The column <code>torodb.container.last_rid</code>.
     */
    public final TableField<R, Integer> LAST_RID 
            = createLastRidField();

    protected abstract TableField<R, String> createDatabaseField();
    protected abstract TableField<R, String> createCollectionField();
    protected abstract TableField<R, TableRefType> createTableRefField();
    protected abstract TableField<R, String> createIdentifierField();
    protected abstract TableField<R, Integer> createLastRidField();

    private final UniqueKeys<TableRefType, R> uniqueKeys;
    
    /**
     * Create a <code>torodb.collections</code> table reference
     */
    public MetaDocPartTable() {
        this(TABLE_NAME, null);
    }

    protected MetaDocPartTable(String alias, Table<R> aliased) {
        this(alias, aliased, null);
    }

    protected MetaDocPartTable(String alias, Table<R> aliased, Field<?>[] parameters) {
        super(alias, TorodbSchema.TORODB, aliased, parameters, "");
        
        this.uniqueKeys = new UniqueKeys<TableRefType, R>(this);
    }
    
    public String getSQLCreationStatement(DatabaseInterface databaseInterface) {
        return databaseInterface.createMetaDocPartTableStatement(getSchema().getName(), getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<R> getPrimaryKey() {
        return uniqueKeys.CONTAINER_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<R>> getKeys() {
        return Arrays.<UniqueKey<R>>asList(uniqueKeys.CONTAINER_PKEY, uniqueKeys.CONTAINER_TABLE_NAME_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract MetaDocPartTable<TableRefType, R> as(String alias);

    /**
     * Rename this table
     */
    public abstract MetaDocPartTable<TableRefType, R> rename(String name);

    public UniqueKeys<TableRefType, R> getUniqueKeys() {
        return uniqueKeys;
    }
    
    public static class UniqueKeys<TableRefType, KeyRecord extends MetaDocPartRecord<TableRefType>> extends AbstractKeys {
        private final UniqueKey<KeyRecord> CONTAINER_PKEY;
        private final UniqueKey<KeyRecord> CONTAINER_TABLE_NAME_UNIQUE;
        
        private UniqueKeys(MetaDocPartTable<TableRefType, KeyRecord> containerTable) {
            CONTAINER_PKEY = createUniqueKey(containerTable, containerTable.DATABASE, containerTable.COLLECTION, containerTable.TABLE_REF);
            CONTAINER_TABLE_NAME_UNIQUE = createUniqueKey(containerTable, containerTable.DATABASE, containerTable.COLLECTION, containerTable.IDENTIFIER);
        }
    }
}
