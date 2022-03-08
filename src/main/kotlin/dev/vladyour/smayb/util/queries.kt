package dev.vladyour.smayb.util

val ACCUMULATED_AMOUNT_QUERY = """
    WITH sum_amount AS (
        SELECT DATE_TRUNC('hour', datetime) + '1 hour' AS datetime,
               SUM(amount)                             AS amount
        FROM history
        WHERE datetime <= :endDatetime
        GROUP BY 1
    ),
         accumulated_amount AS (
             SELECT datetime,
                    SUM(amount) OVER (ORDER BY datetime ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS amount
             FROM sum_amount
         )
    SELECT datetime, amount
    FROM accumulated_amount
    WHERE datetime >= :startDatetime
    ORDER BY datetime
""".trimIndent()

val INSERT_QUERY = """
    INSERT INTO history (amount, datetime)
    VALUES (:amount, :datetime)
""".trimIndent()
