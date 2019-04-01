import * as library from '@signed/library';

export function version() {
    return `version: ${library.version}`;
}

export function helloPaul(): string {
    return library.hello('Paul');
}

export function sum(a: number, b: number): number {
    return a + b;
}
