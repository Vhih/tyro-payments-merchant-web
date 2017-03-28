# Tyro Payments Merchant Web Utility

[![Build Status](https://travis-ci.org/trungie/tyro-payments-merchant-web.svg?branch=master)](https://travis-ci.org/trungie/tyro-payments-merchant-web)

This tool uses selenium webdriver to extract information from the Tyro Payments Merchant website.
 
Supports:
- login
- creating reconciliation reports
- downloading terminal transactions

Requires valid authentication credentials to the Tyro Payments Merchant extranet.

## Usage

### Example: Reconciliation report and download CSV 

```
        TyroLoginPage tyroLoginPage = TyroLoginPage.navigateTo(driver);
        TyroHomePage tyroHomePage = tyroLoginPage.login(username, password);
        TyroReconciliationReportPage tyroReconciliationReportPage = tyroHomePage.navigateToReconciliationReport();

        tyroReconciliationReportPage.clickCreateReport("27/03/2017");

        String totalSale = tyroReconciliationReportPage.getTotalSale();
        System.out.println("totalSale = " + totalSale);

        String[] terminalNames = tyroReconciliationReportPage.getTerminalNames();
        for (String terminalName : terminalNames) {
            TyroTerminalTransactionsPage tyroTerminalTransactionsPage = tyroReconciliationReportPage.getReportAsCsv(terminalName);

            String report = tyroTerminalTransactionsPage.getReportAsCsv();
            System.out.println(terminalName);
            System.out.println(report);
        }

        tyroReconciliationReportPage.navigateToLogout();
```

See tests for further examples.
