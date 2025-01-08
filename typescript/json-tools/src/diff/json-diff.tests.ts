import { describe, test } from 'vitest'
import { DiffStringOptions, diffString } from 'json-diff'
import { deepNestedChange, DiffInput, starWars } from './json.mother.js'

// https://github.com/andreyvit/json-diff

describe('json-diff', () => {
  test('explore', () => {
    const original = { a: 1, b: 'old', match: 'this' }
    const candidate = { b: 'new', c: {}, match: 'this' }
    diffFor(() => ({ original, candidate }), options({ keysOnly: true, excludeKeys: ['a'] }))
  })

  test('StarWars', () => {
    diffFor(starWars, options())
  })

  test('Deep Nested', () => {
    diffFor(deepNestedChange, options())
  })
})

const options = (options: DiffStringOptions = {}): DiffStringOptions => {
  return {
    color: false,
    ...options,
  }
}

const diffFor = (diffInputProvider: () => DiffInput, options?: DiffStringOptions) => {
  const { original, candidate } = diffInputProvider()
  console.log(diffString(original, candidate, options))
}
