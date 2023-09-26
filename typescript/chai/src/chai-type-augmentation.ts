declare global {
    namespace Chai {
        interface AssertionStatic extends AssertionPrototype {
            overwriteProperty(name: string, getter: (this: AssertionStatic, _super: any) => any): void;
        }
        interface ChaiUtils {
            transferFlags(assertion: AssertionStatic, obj: object, includeAll?: boolean): void;
        }
    }
}
