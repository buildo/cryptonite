import * as React from 'react';
import { Tablo, Column, Cell, Header } from 'Basic/Tablo';

export default class Products extends React.PureComponent {
  render() {
    const data = [{ exchange: 'GDAX', product: 'BTC-EUR', bid: '11948.02', ask: '11948.08', value: '5974.01 EUR' }, 
                  { exchange: 'GDAX', product: 'BTC-GBP', bid: '10488.03', ask: '10516.72', value: '5244.015 GBP' },
                  { exchange: 'GDAX', product: 'BTC-USD', bid: '13982.6', ask: '13984.93', value: '6691.3 USD' },
                  { exchange: 'Kraken', product: 'LTC-BTC', bid: '0.016978', ask: '0.017017', value: '0 BTC' },
                  { exchange: 'Kraken', product: 'BTC-EUR', bid: '11763.8', ask: '11763.9', value: '5581.9 EUR' },
                  { exchange: 'Kraken', product: 'XRP-EUR', bid: '1.68000', ask: '1.68698', value: '16.8 EUR' }
                ];
    return ( 
      <Tablo
       data={data}
      >
        <Column name='exchange' width={200}>
          <Header>Exchange</Header>
          <Cell>{ (exchange: string) => exchange}</Cell>
        </Column>

        <Column name='product' width={200}>
          <Header>Products</Header>
          <Cell>{ (product: string) => product}</Cell>
        </Column>

        <Column name='bid' width={200}>
          <Header>Bid</Header>
          <Cell>{ (product: string) => product}</Cell>
        </Column>

        <Column name='ask' width={200}>
          <Header>Ask</Header>
          <Cell>{ (product: string) => product}</Cell>
        </Column>

        <Column name='value' width={200}>
          <Header>Value</Header>
          <Cell>{ (product: string) => product}</Cell>
        </Column>
      </Tablo>
    )
  }
} 
