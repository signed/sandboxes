declare global {
    namespace Chai {
        interface ChaiUtils {
            transferFlags(assertion: AssertionStatic, obj: object, includeAll?: boolean): void;
        }
    }
}
