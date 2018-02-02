import getRoutes from 'metarpheus/api';

export default getRoutes({
  apiEndpoint: '/api',
  timeout: 120000,
  unwrapApiResponse: (resp: any) => resp
});
