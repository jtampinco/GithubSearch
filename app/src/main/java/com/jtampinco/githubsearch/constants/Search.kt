package com.jtampinco.githubsearch.constants


enum class SearchSortBy(val displayValue: String, val value: String) {
    BEST_MATCH("Relevance", "default"),
    UPDATED("Last Updated", "updated"),
    STARS("Stars", "stars"),
    FORKS("Forks", "forks"),
    HELP_WANTED_ISSUES("Help Wanted", "help-wanted-issues"),
}

fun getAllSortByOptions() = SearchSortBy.values().toList()

enum class SearchOrderBy(val displayValue: String, val value: String) {
    DESC("Descending", "desc"),
    ASC("Ascending", "asc")
}

fun getAllOrderByOptions() = SearchOrderBy.values().toList()

enum class SearchFilterBy(val displayValue: String, val value: String) {
    NONE("None", ""),
    KOTLIN("Kotlin", "kotlin"),
    JAVA("Java", "java")
}

fun getAllFilterByOptions() = SearchFilterBy.values().toList()