import { ScisPage } from './app.po';

describe('scis App', () => {
  let page: ScisPage;

  beforeEach(() => {
    page = new ScisPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
