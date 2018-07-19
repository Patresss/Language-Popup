package com.patres.languagepopup.util

import com.patres.languagepopup.model.Fiche
import java.util.*

class RandomGenerator(
        fiche: List<Fiche>
) {

    private val random: Random = Random()
    private val map = TreeMap<Double, Fiche>()
    private var total = 0.0

    init {
        fiche.forEach { add(it) }
    }

    fun generateRandomFiche(): Fiche {
        val value = random.nextDouble() * total
        return map.higherEntry(value).value
    }

    private fun add(fiche: Fiche) {
        total += fiche.levelOfEducationProperty.get().toDoubleOrNull() ?: 1.0
        map[total] = fiche
    }

}
