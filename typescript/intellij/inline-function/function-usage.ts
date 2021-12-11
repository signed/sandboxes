import { toInline } from './contains-function-to-inline'

export const someFunction = () => {
  const tmp = () => 'bogus'
  const result = toInline()
}
