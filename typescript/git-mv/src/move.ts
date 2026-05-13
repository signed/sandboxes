#!/usr/bin/env node

import { promises as fs } from 'node:fs'
import * as path from 'node:path'
//import { spawn } from 'node:child_process'
import { z } from 'zod'

const OptionsSchema = z.object({
  sourceDir: z.string(),
  targetDir: z.string(),
  ignoreFile: z.string(),
})
type Options = z.infer<typeof OptionsSchema>

async function main() {
  const options = parseArgs(process.argv.slice(2))

  const ignorePatterns = await loadIgnorePatterns(options.ignoreFile)

  const entries = await collectEntries(options.sourceDir)

  const filteredEntries = entries.filter((entry) => {
    const relative = path.relative(options.sourceDir, entry)
    return !shouldIgnore(relative, ignorePatterns)
  })

  for (const entry of filteredEntries) {
    const relative = path.relative(options.sourceDir, entry)
    const destination = path.join(options.targetDir, relative)

    await ensureParentDir(destination)

    console.log(`git mv "${entry}" "${destination}"`)

    await gitMove(entry, destination)
  }

  console.log(`Moved ${filteredEntries.length} entries.`)
}

function parseArgs(args: string[]): Options {
  if (args.length < 3) {
    console.error(`
Usage:
  node move.ts <sourceDir> <targetDir> <ignoreFile>

Example:
  node move.ts ./src ./archive ./.moveignore
`)
    process.exit(1)
  }

  return OptionsSchema.parse({
    sourceDir: path.resolve(args[0]!),
    targetDir: path.resolve(args[1]!),
    ignoreFile: path.resolve(args[2]!),
  })
}

async function loadIgnorePatterns(ignoreFile: string): Promise<string[]> {
  const content = await fs.readFile(ignoreFile, 'utf8')

  return content
    .split(/\r?\n/)
    .map((line) => line.trim())
    .filter((line) => line.length > 0 && !line.startsWith('#'))
}

async function collectEntries(dir: string): Promise<string[]> {
  const results: string[] = []

  const items = await fs.readdir(dir, {
    withFileTypes: true,
  })

  for (const item of items) {
    const fullPath = path.join(dir, item.name)

    results.push(fullPath)
  }

  return results
}

function shouldIgnore(relativePath: string, patterns: string[]): boolean {
  const normalized = relativePath.replace(/\\/g, '/')

  return patterns.some((pattern) => {
    const normalizedPattern = pattern.replace(/\\/g, '/')

    // Exact path match
    if (normalized === normalizedPattern) {
      return true
    }

    // Directory prefix match
    if (normalized.startsWith(normalizedPattern + '/')) {
      return true
    }

    // Basename match
    return path.basename(normalized) === normalizedPattern
  })
}

async function ensureParentDir(filePath: string): Promise<void> {
  await fs.mkdir(path.dirname(filePath), {
    recursive: true,
  })
}

async function gitMove(source: string, destination: string): Promise<void> {
  await runCommand('git', ['mv', source, destination])
}

function runCommand(command: string, args: string[]): Promise<void> {
  console.log(command, args)
  return Promise.resolve()
  // return new Promise((resolve, reject) => {
  //   const child = spawn(command, args, {
  //     stdio: 'inherit',
  //     shell: process.platform === 'win32',
  //   })
  //
  //   child.on('close', (code) => {
  //     if (code === 0) {
  //       resolve()
  //     } else {
  //       reject(new Error(`Command failed: ${command} ${args.join(' ')} (exit ${code})`))
  //     }
  //   })
  //
  //   child.on('error', reject)
  // })
}

await main()
