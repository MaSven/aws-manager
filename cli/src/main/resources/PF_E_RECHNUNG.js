(function () {
  var z = document.getElementsByTagName("textarea");
  z[0].value = JSON.stringify({
        "metadata": {
          "type": "SEND_INVOICE_NOTIFICATION",
          "timestamp": "2018-11-28T08:25:56.523Z",
          "traceId": "ac5a9a87-7419-4e60-9747-7dfd7ed0bbd8",
          "commandId": "6b324d22-62c4-459f-a57b-e7b874b8c6a9",
          "version": "0"
        },
        "body": {
          "customer": {
            "customerId": "2c7d9f29-ffad-4258-8a3a-60abf33083da",
            "ecUniqueUserId": "942e2a36-9112-444e-92e3-3bc3cd9307d",
            "invoiceAddress": {
              "salutation": "Mr",
              "firstName": "James",
              "lastName": "Bond",
              "street": "Washington street",
              "addition": "put in my backyard",
              "number": "12",
              "postalCode": "4445511",
              "city": "Washington DC"
            }
          },
          "payment": {
            "method": "INVOICE_INSTALLMENTS",
            "installmentCount": 3
          },
          "invoice": {
            "receiptId": "123!",
            "total": "3921"
          },
          "order": {
            "orderNumber": "6156421",
            "orderDate": "2018-11-28T08:25:56.523Z",
            "partners": [
              {
                "partnerId": "1000624",
                "articles": [
                  {
                    "partnerVariationId": "FB70CD72833E",
                    "positionId": "476bb1ac-a227-4710-8ee4-f297a38a309c",
                    "quantity": 2
                  },
                  {
                    "partnerVariationId": "AB57898544F",
                    "positionId": "476bb1ac-a227-4710-8ee4-f297a38a309b",
                    "quantity": 1
                  },
                  {
                    "partnerVariationId": "AB57898544F",
                    "positionId": "476bb1ac-a227-4710-8ee4-f297a38a309c",
                    "quantity": 1
                  }
                ]
              }
            ]
          }
        }
      },
      null, 2);
})();
