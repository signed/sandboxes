import { lstatSync, readdirSync, readFileSync } from 'fs'
import { JSONSchema4 } from 'json-schema'
import { basename, dirname, extname, join, sep } from 'path'

export type FoundSettingSchema = {
  path: string
  segments: readonly string[]
}

export const readSchema = (filename: string): JSONSchema4 => {
  const contents = readFileSync(filename)
  return JSON.parse(contents.toString())
}

export const findSchemasIn = (settingsBase: string): FoundSettingSchema[] => {
  const schemas = jsonFiles(settingsBase)
  return schemas.map((found) => {
    const sub = dirname(found).replace(settingsBase + '/', '')
    const filename = basename(found, '.json')
    const segments = [...sub.split(sep), filename]
    return { path: found, segments }
  })
}

const jsonFiles = (base: string, found: string[] = []) => {
  const elements = readdirSync(base)
  elements.forEach((element) => {
    const path = join(base, element)
    const stat = lstatSync(path)
    if (stat.isDirectory()) {
      jsonFiles(path, found)
      return
    }
    const s = extname(path)
    if (stat.isFile() && s === '.json') {
      found.push(path)
    }
  })
  return found
}
const relativeSchemasBase = 'src/schema'
export const relativePathToSettingsBase = `${relativeSchemasBase}`
const absolutPathToSchemaBase = `${process.cwd()}/${relativeSchemasBase}`
export const absolutPathToSettingsBase = `${absolutPathToSchemaBase}`
export const absolutPathToSettingsJson = `generated/schema/settings.json`
export const pathToSettingsTs = `generated/typescript/settings.ts`
