import fs from 'fs'
import p from 'path'
import $RefParser from '@apidevtools/json-schema-ref-parser'
import { readSchema } from './shared'

export const ensureCorrectTypePropertyInSettings = () => {
  const settingsBase = p.resolve(process.cwd() + '/schemas/configuration/settings-nested/')
  const schemas = jsonFiles(settingsBase)
  const foundSchemas = schemas.map(found => {
    const sub = p.dirname(found).replace(settingsBase + '/', '')
    const filename = p.basename(found, '.json')
    const segments = [...sub.split(p.sep), filename]
    console.log(segments)
    return { path: found, segments }
  })

  foundSchemas.forEach(async found => {
    await ensureProperType(found)
  })
}

const jsonFiles = (base: string, found: string [] = []) => {
  const elements = fs.readdirSync(base)
  elements.forEach((element) => {
    const path = p.join(base, element)
    const stat = fs.lstatSync(path)
    if (stat.isDirectory()) {
      return jsonFiles(path, found)
    }
    const s = p.extname(path)
    if(stat.isFile() && s === '.json'){
      found.push(path)
    }
  })
  return found
}

const ensureProperType = async function(found: { path: string; segments: string[] }) {
  const schema = readSchema(found.path)
  const parser = new $RefParser()
  const r = await parser.resolve(schema)
  r.set('#/properties/type/const', found.segments.join('.'))
  fs.writeFileSync(found.path, JSON.stringify(schema, null, 2))
}
