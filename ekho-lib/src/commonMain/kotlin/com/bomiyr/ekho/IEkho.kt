package com.bomiyr.ekho

public interface IEkho {
    public val reflectionsCount: Int
    public val reflections: List<EkhoReflection>
    public fun addReflection(reflection: EkhoReflection)
    public fun addReflections(reflections: List<EkhoReflection>)
    public fun removeReflection(reflection: EkhoReflection)
    public fun removeReflections(reflections: List<EkhoReflection>)
    public fun removeAllReflections()
    public fun tagged(tag: String): EkhoLog
}
