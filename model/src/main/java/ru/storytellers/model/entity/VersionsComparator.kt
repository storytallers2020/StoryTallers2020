package ru.storytellers.model.entity

class VersionsComparator (
    val remoteVersions: Versions,
    val localVersions: Versions
)
{
    val isCharacterActual: Boolean
        get() {
            return localVersions.characterVersion == remoteVersions.characterVersion
        }

    val isLocationActual: Boolean
        get() {
            return localVersions.locationVersion == remoteVersions.locationVersion
        }

    val isCoverActual: Boolean
        get() {
            return localVersions.coverVersion == remoteVersions.coverVersion
        }

    val isWordActual: Boolean
        get() {
            return localVersions.rusWordVersion == remoteVersions.rusWordVersion
        }

    fun isActual(): Boolean =
        isCoverActual && isLocationActual && isCharacterActual && isWordActual
}