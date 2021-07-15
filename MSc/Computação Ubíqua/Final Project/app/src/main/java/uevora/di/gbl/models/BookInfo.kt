package uevora.di.gbl.models

data class BookInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val imageLinks: ImageLinks
)
