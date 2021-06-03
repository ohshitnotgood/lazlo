package io.github.praanto__samadder.cocoa.fire

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.praanto__samadder.cocoa.model.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Auth object.
 *
 */


object Auth {
    private val db = Firebase.firestore

    private suspend fun <T> Task<T>.await() : T {
        if (isComplete) {
            val e = exception
            return if (e == null) {
                if (isCanceled) {
                    throw CancellationException("Task cancelled")
                } else {
                    result!!
                }
            } else {
                throw e
            }
        }

        return suspendCancellableCoroutine { cancellable ->
            addOnCompleteListener {
                val e = exception
                if (e == null) {
                    if (isCanceled) cancellable.cancel() else cancellable.resume(result!!)
                } else {
                    cancellable.resumeWithException(e)
                }
            }
        }
    }

    suspend fun fetchDocument (userUID: String, key: String): ArrayPair {
        val querySnapshot = db
            .collection("users").document(userUID).collection(userUID.substring(0, 8) + key)
            .get().await()

        val documentsList = getTaskList(querySnapshot.documents)
        return ArrayPair(documentsList, querySnapshot.documents.size)
    }

    fun pushDocument(key : String, chapterName : String, exerciseTitle : String,
                     details: String, pageIndex: String,
                     assignedDate: String, submissionDate: String, userUID: String,
                     collectionSize: Int, documentId: String, flag: Int, session: String, year: String, hasYearSwitchChecked: Boolean,
                     variant: String
    ) {

        val taskModel = TaskDataModel(chapterName = chapterName, assignedDate = assignedDate,
            submissionDate = submissionDate, documentId = "w" + (collectionSize + 1).toString(), flag = Document.CREATE,
            pageIndex = pageIndex, details = details, exerciseTitle = exerciseTitle, hasYearSwitchChecked = hasYearSwitchChecked,
            session = session, year = year, variant = variant
        )

        when (flag) {
            Document.CREATE -> {
                taskModel.flag = Document.CREATE
                db.collection("users").document(userUID).collection(userUID.substring(0, 8) + key)
                    .document("w" + (collectionSize + 1).toString()).set(taskModel)
            }
            Document.UPDATE -> {
                taskModel.flag = Document.UPDATE
                taskModel.documentId = documentId
                db.collection("users").document(userUID).collection(userUID.substring(0, 8) + key)
                    .document(documentId).set(taskModel)
            }
            Document.DELETE -> {
                taskModel.flag = Document.DELETE
                taskModel.documentId = documentId
                db.collection("users").document(userUID).collection(userUID.substring(0, 8) + key)
                    .document(documentId).set(taskModel)
            }
        }
    }

    /**
     * A nullable suspend function that communicated with FirebaseAuth to authenticate user email and password.
     *
     * Check for null to detect unsuccessful login. Logs error message to Logcat with tag "FirebaseAuthSignInFailed".
     * @param email Email address that the user inputs and is sent to Firebase.
     * @param password Password that the user inputs and is sent to Firebase.
     * @return AuthResult if authentication is successful. Check for null to detect if login was unsuccessful.
     * @exception NullPointerException Function returns null if login is unsuccessful.
     */
    suspend fun signInWithEmailPassword(email : String, password : String) : AuthResult? {
        val auth = Firebase.auth
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Log.e("FirebaseAuthSignInFailed", e.message!!)
            null
        }
    }

    private fun getTaskList(list: List<DocumentSnapshot>) : ArrayList<TaskDataModel> {
        val documentsList = ArrayList<TaskDataModel>()

        for (documents in list) {
            documents.apply {
                val chapterName     = getString(H.chapterName)!!
                val exerciseTitle   = getString(H.exerciseTitle)!!
                val pageIndex       = getString(H.pageIndex)!!
                val details         = getString(H.details)!!
                val assignedDate    = getString(H.assignedDate)!!
                val submissionDate  = getString(H.submissionDate)!!
                val flag            = getLong(H.flag)?.toInt()!!
                val documentId      = getString(H.documentId)!!
                val variant         = getString(H.variant)!!
                val session         = getString(H.session)!!
                val year            = getString(H.year)!!
                val hasYearSwitchChecked      = getBoolean(H.hasYearSwitchChecked)!!

                if (flag != Document.DELETED) {
                    val s = TaskDataModel(
                        chapterName = chapterName, assignedDate = assignedDate, submissionDate = submissionDate, flag = flag,
                        documentId =  documentId, exerciseTitle = exerciseTitle, pageIndex = pageIndex, details = details,
                        hasYearSwitchChecked = hasYearSwitchChecked, variant = variant, year = year, session = session)
                    documentsList.add(s)
                }
            }

        }

        return documentsList
    }

    fun saveToCommon(cred: SensitiveCredentials) {
        db.collection("common").document("cred").set(cred)
    }
}