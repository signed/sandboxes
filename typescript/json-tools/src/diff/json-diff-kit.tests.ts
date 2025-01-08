import { describe, test } from 'vitest'
import { Differ, DifferOptions } from 'json-diff-kit'
import { deepNestedChange, DiffInput, starWars } from './json.mother.js'

// https://github.com/RexSkz/json-diff-kit

describe('json-diff-kit', () => {
  test('explore', () => {
    const original = { a: 1, b: 'old', match: 'this' }
    const candidate = { b: 'new', c: {}, match: 'this' }
    diffFor(() => ({ original, candidate }), options())
  })

  test('StarWars', () => {
    diffFor(starWars, options())
  })

  test('Deep Nested', () => {
    diffFor(deepNestedChange, options())
  })
})

const options = (options: DifferOptions = {}): DifferOptions => {
  return options
}

const diffFor = (diffInputProvider: () => DiffInput, options?: DifferOptions) => {
  const { original, candidate } = diffInputProvider()
  const diff = new Differ(options).diff(original, candidate)
  console.log(JSON.stringify(diff, null, 2))
}
