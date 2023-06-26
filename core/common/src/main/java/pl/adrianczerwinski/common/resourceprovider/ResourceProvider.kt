package pl.adrianczerwinski.common.resourceprovider

interface ResourceProvider {
    fun getString(stringResId: Int): String
    fun getString(stringResId: Int, vararg arguments: Any?): String
}
