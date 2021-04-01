// https://youtrack.jetbrains.com/issue/WEB-50263
interface AnInterface {
    methodToRename():void
}

class AnImplementation implements AnInterface{
    methodToRename(): void {
    }
}
