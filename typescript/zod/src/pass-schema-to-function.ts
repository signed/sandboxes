import {z} from 'zod'


export const UserSchema = z.object({
  id: z.string(),
  username: z.string(),
  createdAt: z.string(),
});
export type User = z.infer<typeof UserSchema>;

export const FruitSchema = z.object({
  color: z.enum(['red', 'green', 'blue']),
  name: z.string()
})
export type Fruit = z.infer<typeof FruitSchema>

export const findOne = async <Schema extends z.ZodTypeAny = z.ZodNever>(
  schema: Schema,
): Promise<z.infer<Schema>> => {
  return schema.parse({}) ;
};
