# Tyro Payments Merchant Web Utility

[![Build Status](https://travis-ci.org/trungie/tyro-payments-merchant-web.svg?branch=master)](https://travis-ci.org/trungie/tyro-payments-merchant-web)

This tool uses selenium webdriver to extract information from the Tyro Payments Merchant website.
 
Supports:
- login
- creating reconciliation reports
- downloading terminal transactions

Requires valid authentication credentials to the Tyro Payments Merchant extranet.

## Usage

Example: Reconciliation report and download CSV 

**Sample code**

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

**Output**
```
totalSale = $1,000,000.00
1 - Terminal ID 1
27/03/2017-09:00:45,Visa,XXXXXXXXXXXX1234,123194,Purchase,Approved,,$1000.00,,,$70.00,Credit,$0.32,$0.37,,$0.69,more&hellip;
27/03/2017-15:35:43,Eftpos,XXXXXXXXXXXX5678,123330,Purchase,Approved,,$9990.00,,,$120.00,eftpos no cashout,$0.06,$0.40,,$0.45,more&hellip;
27/03/2017-20:52:13,Eftpos,XXXXXXXXXXXX0000,123401,Purchase,Approved,,$123123.00,,,$170.00,eftpos no cashout,$0.06,$0.40,,$0.45,more&hellip;
```

[logo]: one-million.png
![logo]


See tests for further examples.
