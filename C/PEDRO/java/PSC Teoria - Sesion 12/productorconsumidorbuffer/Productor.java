package productorconsumidorbuffer;

public class Productor implements Runnable {
    RecursoCompartidoBuffer recurso;

    public Productor(RecursoCompartidoBuffer rc) {
        recurso = rc;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                recurso.almacenar(i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

}
