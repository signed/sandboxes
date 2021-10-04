import { readFileSync } from 'fs'
import { JSONSchema4 } from 'json-schema'
import { dirname, basename, extname, join, sep } from 'path'
import { readdirSync, lstatSync } from 'fs'

export const readSchema = (filename: string): JSONSchema4 => {
  const contents = readFileSync(filename)
  return JSON.parse(contents.toString())
}

export const findSchemasIn = function(settingsBase: string) {
  const schemas = jsonFiles(settingsBase)
  return schemas.map(found => {
    const sub = dirname(found).replace(settingsBase + '/', '')
    const filename = basename(found, '.json')
    const segments = [...sub.split(sep), filename]
    return { path: found, segments }
  })
}


const jsonFiles = (base: string, found: string [] = []) => {
  const elements = readdirSync(base)
  elements.forEach((element) => {
    const path = join(base, element)
    const stat = lstatSync(path)
    if (stat.isDirectory()) {
      return jsonFiles(path, found)
    }
    const s = extname(path)
    if (stat.isFile() && s === '.json') {
      found.push(path)
    }
  })
  return found
}
