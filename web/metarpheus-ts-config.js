const path = require('path');

module.exports = {
  apiPaths: [path.resolve(__dirname, '../api/src/main/scala/cryptonite')],
  modelPrelude: `import * as t from 'io-ts'
export interface Unit {}
export const Unit = t.interface({}, 'Unit');
`, // eslint-disable-line
  modelOut: 'src/app/metarpheus/model-ts.ts',
  wiro: true
};
