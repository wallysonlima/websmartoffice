import { ethers, network } from "hardhat";
import axios from "axios";

async function main() {
  const brlAmount = 1500000; // 💵 Valor em BRL para cada conta
  const targetAddresses = [
    "0xa0Ee7A142d267C1f36714E4a8F75612F20a79720",
    "0x8626f6940E2eb28930eFb4CeF49B2d1F2C9C1199",
    "0xBcd4042DE499D14e55001CcbB24a551F3b954096",
    "0xdF3e18d64BC6A983f673Ab319CCaE4f1a57C7097",
    "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"
  ];

  // 🔍 Busca cotação ETH/BRL
  const response = await axios.get("https://api.binance.com/api/v3/ticker/price?symbol=ETHBRL");
  const ethPriceInBRL = parseFloat(response.data.price);
  console.log(`💱 1 ETH = R$${ethPriceInBRL.toFixed(2)}`);

  // 💰 Converte BRL → ETH
  const ethAmount = brlAmount / ethPriceInBRL;
  console.log(`🧮 R$${brlAmount} = ${ethAmount} ETH`);

  // 🎯 Converte ETH → Wei (BigInt)
  const weiAmount = ethers.parseUnits(ethAmount.toString(), "ether");
  const weiHex = "0x" + BigInt(weiAmount).toString(16);
  console.log(`🧾 Valor em Wei (hex): ${weiHex}`);

  // 🔁 Aplica o valor para todas as contas
  for (const address of targetAddresses) {
    await network.provider.send("hardhat_setBalance", [address, weiHex]);
    console.log(`✅ Saldo da conta ${address} atualizado para ~R$${brlAmount}`);
  }
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
