/*
 *     This file is part of ToroDB.
 *
 *     ToroDB is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ToroDB is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with ToroDB. If not, see <http://www.gnu.org/licenses/>.
 *
 *     Copyright (c) 2014, 8Kdata Technology
 *     
 */

package com.torodb.poc.backend.converters.json;

import org.threeten.bp.Instant;
import org.threeten.bp.format.DateTimeFormatter;

import com.torodb.poc.backend.converters.ValueConverter;
import com.torodb.torod.core.subdocument.values.ScalarInstant;
import com.torodb.torod.core.subdocument.values.heap.InstantScalarInstant;

/**
 *
 */
public class InstantValueToJsonConverter implements
        ValueConverter<String, ScalarInstant> {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<? extends String> getJsonClass() {
        return String.class;
    }

    @Override
    public Class<? extends ScalarInstant> getValueClass() {
        return ScalarInstant.class;
    }

    @Override
    public ScalarInstant toValue(String value) {
        return new InstantScalarInstant(Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(value)));
    }
}
