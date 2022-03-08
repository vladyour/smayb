package dev.vladyour.smayb.scalar

import com.netflix.graphql.dgs.DgsScalar
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@DgsScalar(name = "DateTime")
class ZonedDateTimeScalar: Coercing<ZonedDateTime, String> {
    override fun serialize(zonedDateTime: Any): String {
        return if (zonedDateTime is ZonedDateTime) {
            zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        } else {
            throw CoercingSerializeException("Not a valid ZonedDateTime")
        }
    }

    override fun parseValue(input: Any): ZonedDateTime {
        return try {
            ZonedDateTime.parse(input.toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME)
        } catch (e: DateTimeParseException) {
            throw CoercingParseLiteralException(e.message)
        }
    }

    override fun parseLiteral(input: Any): ZonedDateTime {
        if (input is StringValue) {
            return ZonedDateTime.parse(input.value, DateTimeFormatter.ISO_ZONED_DATE_TIME)
        }

        throw CoercingParseLiteralException("Value is not a valid ISO date time")
    }
}
