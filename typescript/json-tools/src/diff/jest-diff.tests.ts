import { describe, test } from 'vitest'
import { deepNestedChange, DiffInput, starWars } from './json.mother.js'
import { diff, DiffOptions } from 'jest-diff'

// https://github.com/andreyvit/json-diff

const noColor = (text: string) => text
const asIsPropertyOrder = () => 0

describe('jest-diff', () => {
  test('explore', () => {
    const original = { a: 1, b: 'old', match: 'this' }
    const candidate = { b: 'new', c: {}, match: 'this' }
    diffFor(() => ({ original, candidate }), options())
  })

  test('StarWars', () => {
    diffFor(
      starWars,
      options({
        aColor: noColor,
        bColor: noColor,
        changeColor: noColor,
        commonColor: noColor,
        patchColor: noColor,
        omitAnnotationLines: true,
        aAnnotation: 'Original',
        bAnnotation: 'Modified',
        compareKeys: asIsPropertyOrder,
        contextLines: 0,
      }),
    )
  })

  test('Deep Nested', () => {
    diffFor(deepNestedChange, options())
  })
})

const options = (options: DiffOptions = {}): DiffOptions => {
  return {
    aColor: noColor,
    bColor: noColor,
    changeColor: noColor,
    commonColor: noColor,
    patchColor: noColor,
    omitAnnotationLines: true,
    aAnnotation: 'Original',
    bAnnotation: 'Modified',
    compareKeys: asIsPropertyOrder,
    contextLines: 0,
    ...options,
  }
}

const diffFor = (diffInputProvider: () => DiffInput, options?: DiffOptions) => {
  const { original, candidate } = diffInputProvider()
  console.log(diff(original, candidate, options))
}
