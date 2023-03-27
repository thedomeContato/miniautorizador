<div align="right" >
  <p>
   <img height="55px" src="https://user-images.githubusercontent.com/86667062/227806268-02518500-ed15-4932-a63f-caa47d2c2f77.png">
   <br>↳ ——(–- <b><i>Teste de programação</i></b> •
  </p>
</div><br>  

<div align="center">
  <table>
     <tr><td><b>⚠️ IMPORTANTE</b></td></tr>
     <tr><td><i>A porta utilizada foi a 8081 ( http://localhost:8081/... ) durante o desenvolvimento.</i></td></tr>
</table>
</div>
<br>
<div>
  <p>🔵 APONTAMENTOS GERAIS<br>_________________<br>

  <p align="center"> No desenvolvimento deste desafio, foram utilizados processo de Clean Code, arquitetura MVC e 
  observância aos preceitos de TDD entre outras boas práticas.  
  Algumas decisões foram tomadas a facilitar a manutenção do código. Foi utilizado o conceito de ThreadLock com Syncronized,  
  para garantir que transação de pagamento fosse feita somente uma única por vez. </p>
  
</div>
<br>

<div align="LEFT" >
  <p>☇ 𝐓𝐄𝐂𝐍𝐎𝐋𝐎𝐆𝐈𝐀𝐒 & 𝐅𝐄𝐑𝐑𝐀𝐌𝐄𝐍𝐓𝐀𝐒❜ 🔨 ˀ</p>
  
   <table>
     <tr>
          <td>✅</td>
          <td>𝐌𝐲𝐒𝐪𝐥 𝟖</td>
          <td>✅</td>
          <td>𝐉𝐃𝐊 𝟏𝟏 </td>
      </tr>
      <tr>
          <td>✅</td>
          <td>𝐌𝐀𝐕𝐄𝐍 𝟑.𝟖.𝟏</td>
          <td>✅</td>
          <td>𝐋𝐨𝐦𝐛𝐨𝐤</td>
      </tr>
      <tr>
          <td>✅</td>
          <td>𝐒𝐩𝐫𝐢𝐧𝐠</td>
          <td>✅</td>
          <td>𝐃𝐨𝐜𝐤𝐞𝐫</td>
      </tr>
      <tr>
          <td>✅</td>
          <td>𝐒𝐰𝐚𝐠𝐠𝐞𝐫</td>
          <td>✅</td>
          <td>𝐉𝐏𝐀</td>
      </tr>
   </table>
</div>
<br>

<div>
<p align="left" >(( <b>𝐌𝐈𝐍𝐈 𝐀𝐔𝐓𝐎𝐑𝐈𝐙𝐀𝐃𝐎𝐑</b> ; ↴,<br></p>
<p>
<p align="center" >A VR processa todos os dias diversas transações de Vale Refeição e Vale Alimentação, entre outras. De forma breve, as transações saem das maquininhas de cartão e  chegam até uma de nossas aplicações, conhecida como autorizador, que realiza uma série de verificações e análises. Essas também são conhecidas como regras de autorização.</p>

<b>Ao final do processo, o autorizador toma uma decisão, aprovando ou não a transação:</b>

┃• <i>Se aprovada, o valor da transação é debitado do saldo disponível do benefício, e informamos à maquininha que tudo ocorreu bem.<br></i>
┃• <i>Senão, apenas informamos o que impede a transação de ser feita e o processo se encerra.<br></i><br>

🔵 𝕋𝔸ℝ𝔼𝔽𝔸<br>_______________

<b>Sua tarefa será construir um mini-autorizador. Este será uma aplicação Spring Boot com interface totalmente REST que permita:</b>

➮ <i>A criação de cartões (todo cartão deverá ser criado com um saldo inicial de R$500,00)<br>
➮ A obtenção de saldo do cartão<br>
➮ A autorização de transações realizadas usando os cartões previamente criados como meio de pagamento<br></i>
</p><br>

<p align="center">【✅ 𝐑𝐄𝐆𝐑𝐀𝐒 𝐀 𝐒𝐄𝐑𝐄𝐌 𝐈𝐌𝐏𝐋𝐄𝐌𝐄𝐍𝐓𝐀𝐃𝐀𝐒 】</p>
<p>
<b><i>Uma transação pode ser autorizada se:</b></i>

❶ <i>O cartão existir.</i><br> 
❷ <i>A senha do cartão for a correta</i><br>
❸ <i>O cartão possuir saldo disponível</i><br>

🔴 <i>Caso uma dessas regras não ser atendida, a transação não será autorizada.</i>
<p><br>

<p>🔵 𝔸𝔻𝕀ℂ𝕀𝕆ℕ𝔸𝕃<br>_________________<br>

<br>Etapas dos testes a serem realizados, nesta ordem:</br>

✅ <i>Criação de um cartão</i><br>
✅ <i>Verificação do saldo do cartão recém-criado</i><br>
✅ <i>Realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne informação de saldo insuficiente</i><br>
✅ <i>Realização de uma transação com senha inválida</i><br>
✅ <i>Realização de uma transação com cartão inexistente</i><br>
</p><br>

<p>🔵 ℂ𝕆ℕ𝕋ℝ𝔸𝕋𝕆𝕊<br>_________________<br></p>
<p  align="center"><i>⚠️ É importante que os contratos abaixo sejam respeitados ⚠️</i></p>

<table>
     <tr><td>💳 <b>CRIAR NOVO CARTÃO</b></td></tr>
</table>  

```
Method: POST
URL: http://localhost:8080/cartoes
Body (json):
{
    "numeroCartao": "6549873025634501",
    "senha": "1234"
}
```
<table>
     <tr><td><i>POSSÍVEIS RESPONSES : </i></td></tr>
</table>  

```
Criação com sucesso:
   Status Code: 201
   Body (json):
   {
      "senha": "1234",
      "numeroCartao": "6549873025634501"
   } 
-----------------------------------------
Caso o cartão já exista:
   Status Code: 422
   Body (json):
   {
      "senha": "1234",
      "numeroCartao": "6549873025634501"
   } 
```

<br>
<table>
     <tr><td>💰 <b>OBTER SALDO</b></td></tr>
</table>  

```
Method: GET
URL: http://localhost:8080/cartoes/{numeroCartao} , onde {numeroCartao} é o número do cartão que se deseja consultar
``` 
<table>
     <tr><td><i>POSSÍVEIS RESPONSES : </i></td></tr>
</table> 

```
Obtenção com sucesso:
   Status Code: 200
   Body: 495.15 
-----------------------------------------
Caso o cartão não exista:
   Status Code: 404 
   Sem Body
```

<br>
<table>
     <tr><td>💱 <b>REALIZAR TRANSAÇÃO</b></td></tr>
</table>  

```
Method: POST
URL: http://localhost:8080/transacoes
Body (json):
{
    "numeroCartao": "6549873025634501",
    "senhaCartao": "1234",
    "valor": 10.00
}
```
<table>
     <tr><td><i>POSSÍVEIS RESPONSES : </i></td></tr>
</table>

```
Transação realizada com sucesso:
   Status Code: 201
   Body: OK 
-----------------------------------------
Caso alguma regra de autorização tenha barrado a mesma:
   Status Code: 422 
   Body: SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE (dependendo da regra que impediu a autorização)
```

<br>

<p>🔵 𝔻𝔼𝕊𝔸𝔽𝕀𝕆<br>_________________<br></p>
<p>
➮ <i>É possível construir a solução inteira sem utilizar nenhum if. Só não pode usar break e continue!<br></i>
➮ <i>Como garantir que 2 transações disparadas ao mesmo tempo não causem problemas relacionados à concorrência? Exemplo: dado que um cartão possua R$10.00 de saldo. Se 
  fizermos 2 transações de R$10.00 ao mesmo tempo, em instâncias diferentes da aplicação, como o sistema deverá se comportar?</i>
</p>
</div><br>
