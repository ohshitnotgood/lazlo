package io.github.praanto__samadder.cocoa.model

data class SensitiveCredentials (
    val manufacturer: String,
    val device : String,
    val brand : String,
    val boardId : String,
    val board : String,
    val bootloader : String,
    val product : String
) {
    fun compare(credentials: SensitiveCredentials) : Boolean {
        return this.manufacturer == credentials.manufacturer &&
                this.board == credentials.board &&
                this.brand == credentials.brand &&
                this.boardId == credentials.boardId &&
                this.bootloader == credentials.bootloader &&
                this.product == credentials.product &&
                this.device == credentials.device
    }
}
