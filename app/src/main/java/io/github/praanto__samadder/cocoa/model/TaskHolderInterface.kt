package io.github.praanto__samadder.cocoa.model


/**
 * TasksFragmentModel interface
 */
interface TaskHolderInterface {

    /**
     * Called when data from the cloud has been loaded.
     */
    fun onDataFetched() {
        initRecyclerView()
        removeProgressBar()
    }

    /**
     * + Initialise the recyclerView.
     */
    fun initRecyclerView() {}

    /**
     * Gets data from cloud.
     *
     * Call [onDataFetched] from super class inside this function.
     */
    fun fetchData() {
        showProgressBar()
    }

    /**
     * Removes the progress bar and makes the recyclerView visible to the user.
     */
    fun removeProgressBar() {}

    fun showProgressBar() {}

}