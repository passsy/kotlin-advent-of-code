package y2017

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import y2017.Day20_ParticleSwarm.Particle
import y2017.Day20_ParticleSwarm.Vector
import y2017.Day20_ParticleSwarm.closesParticleLongTerm
import y2017.Day20_ParticleSwarm.leftoversAfterCollisions
import y2017.Day20_ParticleSwarm.part1
import y2017.Day20_ParticleSwarm.part2
import y2017.Day20_ParticleSwarm.toParticle


class Day20_ParticleSwarmTest {

    @Test
    fun `parse particle`() {
        val parsed = toParticle(0, "p=<-671,1794,-1062>, v=<46,87,41>, a=<-1,-17,1>")
        val particle = Particle(0, Vector(-671, 1794, -1062), Vector(46, 87, 41),
                Vector(-1, -17, 1))
        assertThat(parsed).isEqualTo(particle)
    }

    @Test
    fun `sample 1`() {
        val particles = listOf(
                Particle(0, Vector(-3, 0, 0), Vector(2, 0, 0), Vector(-1, 0, 0)),
                Particle(1, Vector(-4, 0, 0), Vector(0, 0, 0), Vector(-2, 0, 0))
        )
        assertThat(closesParticleLongTerm(particles).id).isEqualTo(0)
    }

    @Test
    fun `solve part 1`() {
        assertThat(part1.test()).isEqualTo("144")
    }

    @Test
    fun `sample 2`() {
        val particles = listOf(
                Particle(0, Vector(-6, 0, 0), Vector(3, 0, 0), Vector(0, 0, 0)),
                Particle(1, Vector(-4, 0, 0), Vector(2, 0, 0), Vector(0, 0, 0)),
                Particle(2, Vector(-2, 0, 0), Vector(1, 0, 0), Vector(0, 0, 0)),
                Particle(3, Vector(3, 0, 0), Vector(-1, 0, 0), Vector(0, 0, 0)))

        assertThat(leftoversAfterCollisions(particles).map { it.id }).isEqualTo(listOf(3))
    }

    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("477")
    }
}