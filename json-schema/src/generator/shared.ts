import { readFileSync } from 'fs'
import { JSONSchema4 } from 'json-schema'

export const readSchema = (filename: string): JSONSchema4 => {
  const contents = readFileSync(filename)
  return JSON.parse(contents.toString())
}
