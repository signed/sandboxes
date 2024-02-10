import { describe, test, expect } from 'vitest'
import { hello } from './index.js'
import * as E from 'fp-ts/Either'
import {flow} from 'fp-ts/function'

const name: string = 'Susan'

describe('one', () => {
  test('should fly', () => {
    E.left()
    expect(hello(name)).toBe('Hello Susan!')
  })
})
