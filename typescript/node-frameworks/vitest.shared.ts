import { mergeConfig, type UserWorkspaceConfig } from 'vitest/config'

/**
 * The official vitest defineProject function takes and returns UserProjectConfigExport
 * The widder return type also allows Promise<UserWorkspaceConfig> and others that are not
 * compatible with mergeConfig.
 *
 * Solution until those features are actually needed, use a
 * custom defineProject only supporting <code>UserWorkspaceConfig<code>
 */
export const defineProject = (config: UserWorkspaceConfig) => {
  return config
}

// https://vitest.dev/config/
const sharedConfiguration = defineProject({
  test: {
    include: ['**/*.tests.ts'],
  },
})

export const mergeDefaultsWith = (overrides: UserWorkspaceConfig) => {
  return mergeConfig(sharedConfiguration, overrides)
}
