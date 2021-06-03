package io.github.praanto__samadder.cocoa.model

/**
 * @param chapterName Title shown in each card in the recyclerView.
 * @param submissionDate Submission Date shown in each card in the recyclerView as the subtitle.
 * @param assignedDate Date on which the task was assigned. Does not appear in the recyclerView but
 * @param flag Signal to database that defines the action with the data.
 * @param documentId Id of the document the data is stored inside Firestore
 * does in the record editor.
 */
data class TaskDataModel constructor(
    val chapterName: String,
    val exerciseTitle: String,
    val details: String,
    val pageIndex : String,
    val assignedDate: String,
    val submissionDate : String,
    var documentId : String,
    val hasYearSwitchChecked : Boolean,
    val session: String,
    val variant: String,
    val year: String,
    var flag : Int
)

object Document {
    const val CREATED           = 2
    const val DELETED           = -1

    const val NO_NEED_COLLECTION_SIZE = 0

    const val CREATE            = 2
    const val UPDATE            = 1
    const val DELETE            = -1
}

object H {
    const val session           = "session"
    const val year              = "year"
    const val variant           = "variant"
    const val submissionDate    = "submissionDate"
    const val assignedDate      = "assignedDate"
    const val documentId        = "documentId"
    const val flag              = "flag"
    const val chapterName       = "chapterName"
    const val details           = "details"
    const val pageIndex         = "pageIndex"
    const val hasYearSwitchChecked = "hasYearSwitchChecked"
    const val fragmentName      = "fragmentName"
    const val exerciseTitle     = "exerciseTitle"
    const val collectionSize    = "collectionSize"

    const val initTag           = "PagingRecyclerViewInitializer"
}

object SubjectKey {
    const val physics           = "_phy"
    const val chemistry         = "_chem"
    const val pureMathematics   = "_p3"
    const val statistics        = "_stats"
}

object Session {
    const val feb = "Feb/Mar"
    const val nov = "Nov/Dec"
    const val may = "May/June"
}