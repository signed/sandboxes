import { describe, test } from 'vitest'
import { DiffOptions, diffString } from 'json-diff'
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

const options = (options: DiffOptions = {}): DiffOptions => {
  return options
}

const diffFor = (diffInputProvider: () => DiffInput, options?: DiffOptions) => {
  const { original, candidate } = diffInputProvider()
  console.log(diffString(original, candidate, options))
}
