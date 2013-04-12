1. create wrapping class that extends the class to start from
    1. Create the constructors als used by the class to be replaced
1. Replace Constructor calls
    1. Replace two argument constructor
        * new SimpleDateFormat($string$, $locale$)
        * new FormatWrapper($string$, $locale$)
    1. Replace one argument constructor
        * new SimpleDateFormat($string$)
        * new FormatWrapper($string$)
1. For Each method/constructor that takes a ClassToReplace add a second method with the same parameter
    * void method(Old param){
        //do stuff
      }
    * void method(Old param){
         this.method((New) param)); //this cast will never fail because we replaced all constructor calls
      }
      void method(New param){
        //do stuff
      }
1. Switch variable declarations over to the Migration type
    1. Declarations that are initialized on Declaration
        * SimpleDateFormat $variablename$ = new FormatWrapper($param$);
        * FormatWrapper $variablename$ = new FormatWrapper($param$);
    1. Fields in class that are not initialized on declaration
        * class $Class$ {
            @Modifier("Instance") $FieldType$ $FieldName$ = $Init$;
          }
        * class $Class$ {
            @Modifier("Instance") FormatWrapper $FieldName$ = $Init$;
          }

     1.