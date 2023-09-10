package com.videoview.common

import timber.log.Timber

class NumberedTimberTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "%s:%s",
            element.fileName,
            element.lineNumber
        )
    }
}