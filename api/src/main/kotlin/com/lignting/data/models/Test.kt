package com.lignting.com.lignting.data.models

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id

/**
 * Test is a data model representing a simple entity with an ID and text field.
 */
@Entity
interface Test {
    @Id
    val id: Long
    val text: String
}