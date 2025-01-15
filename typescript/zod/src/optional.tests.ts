import {describe, expect, test} from "vitest";
import {z} from 'zod'


const OptionalPropertySchema = z.object({
  property: z.string().optional()
});

type OptionalProperty = z.infer<typeof OptionalPropertySchema>

describe('optional property', () => {
  function parsedObject(data: unknown): OptionalProperty {
    return OptionalPropertySchema.parse(data);
  }

  test('completely missing property', () => {
    expect(parsedObject({}).property).toBeUndefined
  });

  test('null as property value', () => {
    expect(parsedObject({property: undefined}).property).toBeUndefined
  });

  test('value present', () => {
    expect(parsedObject({property: 'content'}).property).toEqual('content')
  });
});
