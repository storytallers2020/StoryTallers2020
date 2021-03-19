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

    val isRusWordActual: Boolean
        get() {
            return localVersions.rusWordVersion == remoteVersions.rusWordVersion
        }

    val isEngWordActual: Boolean
        get() {
            return localVersions.engWordVersion == remoteVersions.engWordVersion
        }

    val isThirdLevelWordActual: Boolean
        get() {
            return localVersions.thirdLevelWordVersion == remoteVersions.thirdLevelWordVersion
        }

}