package apackage

class WorldController {
    static scaffold = true


    def dependency(){
        render{
            new SomeClassToDependOn().dumpOutput()
        }
    }

}
