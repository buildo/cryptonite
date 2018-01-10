import * as React from 'react';
import { IntlProvider } from 'Basic';
import Products from 'Products';

type Props = {
  messages: any,
  locales: string[],
  formats: any,
  locale: string
};

export default class App extends React.Component<Props> {
  render() {
    return (
      <IntlProvider {...this.props}>
        <div className='layout'>
          <Products />
        </div>
      </IntlProvider>
    );
  }
}
