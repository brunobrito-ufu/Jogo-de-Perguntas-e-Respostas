import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Map;

public class Arquivos implements Serializable {
    private static Arquivos instancia;
    public static Arquivos getInstancia() {
        if (instancia == null) {
            instancia = new Arquivos();
        }
        return instancia;
    }
    public void EscritaPerguntas(Jogo jogo){
        FileOutputStream escritorArquivo=null;
        ObjectOutputStream escritorObj;
        try {
            escritorArquivo = new FileOutputStream("perguntas.dat");
            escritorObj = new ObjectOutputStream(escritorArquivo);

            escritorObj.writeObject(jogo.getListaPerguntas());

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(escritorArquivo!=null)
                    escritorArquivo.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void LeituraPergunta(Jogo jogo){
        ObjectInputStream leitorObj;
        FileInputStream leitorArquivo=null;
        try{
            leitorArquivo=new FileInputStream("perguntas.dat");
            leitorObj=new ObjectInputStream(leitorArquivo);
            ArrayList<Pergunta> listaPerguntas;

            listaPerguntas = (ArrayList<Pergunta>) leitorObj.readObject();

            jogo.setListaPerguntas(listaPerguntas);

        }catch(EOFException e){
            try{
                leitorArquivo.close();
            }catch(IOException ex){System.out.println(ex.getMessage());}
        }catch(Exception ex){System.out.println(ex.getMessage());
        }finally
        {try{
            if(leitorArquivo!=null)leitorArquivo.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    }

    public Map<String, ArrayList<Object>> LeituraJogador() {
        ObjectInputStream leitorObj;
        FileInputStream leitorArquivo = null;
        HashMap<String, ArrayList<Object>> listaJogadores = null;
        try {
            leitorArquivo = new FileInputStream("jogador.dat");
            leitorObj = new ObjectInputStream(leitorArquivo);

            listaJogadores = (HashMap<String, ArrayList<Object>>) leitorObj.readObject();

        } catch (EOFException e) {
            try {
                leitorArquivo.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (leitorArquivo != null) leitorArquivo.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return listaJogadores;
    }

    public void EscritaJogadores(Jogo jogo){
        FileOutputStream escritorArquivo=null;
        ObjectOutputStream escritorObj;
        try {
            escritorArquivo = new FileOutputStream("jogador.dat");
            escritorObj = new ObjectOutputStream(escritorArquivo);

            if((this.LeituraJogador() != null) || (jogo.getMapJogadoresPontos() != null)){
                escritorObj.writeObject(jogo.getMapJogadoresPontos());
            }else{
                Map<String, ArrayList<Object>> newMap = this.SomaPontos(this.LeituraJogador(), jogo.getMapJogadoresPontos());
                escritorObj.writeObject(newMap);
            }

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(escritorArquivo!=null)
                    escritorArquivo.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void EscritaAdmin(Jogo jogo){
        FileOutputStream escritorArquivo=null;
        ObjectOutputStream escritorObj;
        try {
            escritorArquivo = new FileOutputStream("admin.dat");
            escritorObj = new ObjectOutputStream(escritorArquivo);

            escritorObj.writeObject(jogo.getAdmin());

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(escritorArquivo!=null)
                    escritorArquivo.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void LeituraAdmin(Jogo jogo){
        ObjectInputStream leitorObj;
        FileInputStream leitorArquivo=null;
        try{
            leitorArquivo=new FileInputStream("admin.dat");
            leitorObj=new ObjectInputStream(leitorArquivo);

            Admin adm = (Admin) leitorObj.readObject();

            jogo.setAdmin(adm);

        }catch(EOFException e){
            try{
                leitorArquivo.close();
            }catch(IOException ex){System.out.println(ex.getMessage());}
        }catch(Exception ex){System.out.println(ex.getMessage());
        }finally
        {try{
            if(leitorArquivo!=null)leitorArquivo.close();
        }catch(Exception e) {
        }
        }
    }

    public void preencheJogadores(Jogo jogo){
        if(this.LeituraJogador() != null) {
            Map<String, ArrayList<Object>> map = this.ZeraPontos(this.LeituraJogador());
            jogo.setMapJogadoresPontos(map);
        }
    }

    public Map<String, ArrayList<Object>> ZeraPontos(Map<String, ArrayList<Object>> map){

            for (Map.Entry<String, ArrayList<Object>> entry : map.entrySet()) {
                String nomeJogador = entry.getKey();
                map.get(nomeJogador).add(1, 0);
            }

        return map;
    }

    public Map<String, ArrayList<Object>> SomaPontos(Map<String, ArrayList<Object>> MapArquivo, Map<String, ArrayList<Object>> lastMap){

        for (Map.Entry<String, ArrayList<Object>> entry : lastMap.entrySet()) {
            String nomeJogador = entry.getKey();
            int pontosMapArquivo = 0;
            if(MapArquivo.containsKey(nomeJogador)){
                pontosMapArquivo =(int) MapArquivo.get(nomeJogador).get(1);
            }
            int soma = (pontosMapArquivo + (int) lastMap.get(nomeJogador).get(1));
            lastMap.get(nomeJogador).set(1,soma);
        }
        return lastMap;
    }


}
