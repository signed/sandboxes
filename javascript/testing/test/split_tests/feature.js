import {suite, test, setup, teardown, suiteSetup, suiteTeardown} from 'mocha';

import Get from './feature_get';
import Post from './feature_post';

suite('feature', function () {
  Get();
  Post();
});
