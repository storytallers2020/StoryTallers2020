package ru.storytellers.model.entity

class VersionsComparator (
    val remoteVersions: Versions,
    private val localVersions: Versions
)
{
    private val isCharacterActual: Boolean
        get() {
            return localVersions.characterVersion == remoteVersions.characterVersion
        }

    private val isLocationActual: Boolean
        get() {
            return localVersions.locationVersion == remoteVersions.locationVersion
        }

    private val isCoverActual: Boolean
        get() {
            return localVersions.coverVersion == remoteVersions.coverVersion
        }

    private val isWordActual: Boolean
        get() {
            return localVersions.rusWordVersion == remoteVersions.rusWordVersion
        }

    fun isActual(): Boolean =
        isCoverActual && isLocationActual && isCharacterActual && isWordActual
}