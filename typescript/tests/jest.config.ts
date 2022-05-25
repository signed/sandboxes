import type { Config } from '@jest/types';

export default async (): Promise<Config.InitialOptions> => {
  return {
    testRegex: '.*\\.tests\\.ts$',
    moduleFileExtensions: ['ts', 'tsx', 'js'],
    verbose: true
  };
};
