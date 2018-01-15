import init from 'buildo-state/lib';
import * as t from 'tcomb';

export interface AppState {
  view: string
};
export const AppState = t.interface<AppState>({
  view: t.String
}, { strict: true });

export const { run, connect, appState } = init<AppState>(AppState);
import { TransitionFunction } from 'buildo-state/lib/transition';
export type TransitionFunction = TransitionFunction<AppState>;
