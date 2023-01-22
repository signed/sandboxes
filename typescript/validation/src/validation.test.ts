import { describe, expect, it, test } from 'vitest'
import { z, ZodError } from 'zod'

describe('string schema', () => {
  const stringSchema = z.string()

  describe('parse', () => {
    it('string successful', () => {
      const input: unknown = 'tuna'
      const parsed = stringSchema.parse(input)
      //    ^? const parsed: string
      expect(parsed).toEqual('tuna')
    })

    it('number with error', () => {
      const input: unknown = 12
      expect(() => stringSchema.parse(input)).toThrow(ZodError)
    })
  })

  describe('safeParse', () => {
    it('string successful', () => {
      const input: unknown = 'tuna'
      const parseResult = stringSchema.safeParse(input)
      expect(parseResult.success).toBe(true)
      if (parseResult.success) {
        expect(parseResult.data).toEqual('tuna')
      }
    })

    it('number with error', () => {
      const input: unknown = 12
      const parseResult = stringSchema.safeParse(input)
      expect(parseResult.success).toBe(false)
      if (!parseResult.success) {
        expect(parseResult.error).toBeDefined()
      }
    })
  })
})

describe('cope if expected string array is corrupted', () => {
  const schema = z.preprocess((data) => {
    if (Array.isArray(data)) {
      return data as unknown
    }
    // do error reporting
    return []
  }, z.array(z.string()))
  type Cleaned = z.infer<typeof schema>
  //    ^? type Cleaned = string[]

  test('string', () => {
    const parsed: Cleaned = schema.parse('corrupted')
    expect(parsed).toHaveLength(0)
  })
  test('object', () => {
    const parsed: Cleaned = schema.parse({ k: 'corrupted' })
    expect(parsed).toHaveLength(0)
  })
  describe('expected array', () => {
    test('empty array', () => {
      const parsed: Cleaned = schema.parse([])
      expect(parsed).toHaveLength(0)
    })
    test('array with string', () => {
      const parsed: Cleaned = schema.parse(['one'])
      expect(parsed).toEqual(['one'])
    })
    test('array with number', () => {
      const result = schema.safeParse([42])
      if (result.success) {
        expect.fail('parse should fail')
      }
      const issues = result.error.issues
      expect(issues).toHaveLength(1)
      const issue = issues[0]
      expect(issue).toStrictEqual({
        code: 'invalid_type',
        expected: 'string',
        received: 'number',
        path: [0],
        message: 'Expected string, received number',
      })
    })
  })
})
