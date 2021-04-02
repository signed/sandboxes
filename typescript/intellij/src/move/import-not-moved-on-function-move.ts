// https://youtrack.jetbrains.com/issue/WEB-50266
import { ReadableSignal, Signal } from 'micro-signals';

export const functionToMove = <T>(signal:ReadableSignal<T>) => {
    return signal.readOnly()
}

functionToMove(new Signal<string>())
